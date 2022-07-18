/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Note
import com.bionova.optimi.core.domain.mongo.NoteFile
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.Constants
import grails.plugin.springsecurity.annotation.Secured
import org.apache.commons.io.FilenameUtils
import org.bson.Document
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

import javax.servlet.http.HttpServletRequest

/**
 * @author Pasi-Markus Mäkelä
 */
class NoteService {

    def userService
    def fileUtil
    def domainClassService

    @Secured(["ROLE_AUTHENTICATED"])
    def addNoteToEntity(String entityId, Note note, HttpServletRequest request) {
        if (entityId && note) {
            def date = new Date()
            note.lastUpdated = date
            note.dateCreated = date
            User user = userService.getCurrentUser()
            note.creatorId = user?.id?.toString()
            note.lastUpdaterId = user?.id?.toString()
            boolean fileOk = true

            if (request instanceof MultipartHttpServletRequest) {
                fileOk = addFile(note, request)
            }

            if (fileOk && note.validate()) {
                if (note.targetEntityId) {
                    Entity entity = Entity.get(note.targetEntityId)

                    if (entity) {
                        note.entityName = entity.operatingPeriodAndName
                        entity.targetedNote = note.comment
                        entity.merge(flush:true, failOnError: true)
                    }
                }
                note.previousTargetEntityId = note.targetEntityId
                note = note.save(flush: true, failOnError: true)

                if (note) {
                    Entity entity = Entity.get(entityId)

                    if (entity.noteIds) {
                        entity.noteIds.add(note.id.toString())
                    } else {
                        entity.noteIds = [note.id.toString()]
                    }
                    entity.merge(failOnError: true)
                }
            }
        }
        return note
    }

    private Document getAsDocument(Note note) {
        Document document = new Document()
        List<PersistentProperty> notePersistentProperties = domainClassService.getPersistentPropertiesForDomainClass(Note.class)

        notePersistentProperties?.each { PersistentProperty domainClassProperty ->
            if (note[domainClassProperty.name]) {
                document.append(domainClassProperty.name, note[domainClassProperty.name])
            }
        }
        return document
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def getNoteById(String noteId) {
        if (noteId) {
            return Note.get(noteId)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def updateNote(Note note, HttpServletRequest request) {
        boolean updateOk = false

        if (note?.id) {
            note.lastUpdated = new Date()
            User user = userService.getCurrentUser()
            note.lastUpdaterId = user?.id?.toString()
            boolean fileOk = true

            if (request instanceof MultipartHttpServletRequest) {
                fileOk = addFile(note, request)
            }

            if (fileOk && note.validate()) {
                if (note.targetEntityId) {
                    Entity entity = Entity.get(note.targetEntityId)

                    if (entity) {
                        note.entityName = entity.operatingPeriodAndName
                        entity.targetedNote = note.comment
                        entity = entity.merge(failOnError: true)
                    }
                }

                if (note.previousTargetEntityId && !note.previousTargetEntityId.equals(note.targetEntityId)) {
                    Entity oldEntity = Entity.get(note.previousTargetEntityId)

                    if (oldEntity) {
                        oldEntity.targetedNote = null
                        oldEntity.merge(failOnError: true)
                    }
                }
                note.previousTargetEntityId = note.targetEntityId
                note = note.merge(flush: true)
                updateOk = true
            } else {
                updateOk = false
            }
        }
        return updateOk
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def removeNote(String entityId, String noteId) {
        if (entityId && noteId) {
            Entity entity = Entity.get(entityId)
            entity?.noteIds?.remove(noteId)
            entity.merge(flush: true)
            Note note = Note.get(noteId)

            if (note) {
                if (note.targetEntityId) {
                    Entity targetEntity = Entity.get(note.targetEntityId)

                    if (targetEntity) {
                        targetEntity.targetedNote = null
                        targetEntity.merge(flush:true)
                    }

                }
                note.delete(flush: true)
            }
        }
    }

    private boolean addFile(Note note, MultipartHttpServletRequest request) {
        boolean fileOk = true
        Map fileMap = fileUtil.getMultiPartFilesFromRequest(request)

        if (fileMap) {
            fileMap?.each { String fileName, MultipartFile mpFile ->
                if (mpFile && mpFile.size > 0) {
                    NoteFile file = new NoteFile()
                    file.name = mpFile.originalFilename
                    file.data = mpFile.bytes
                    file.contentType = mpFile.contentType

                    if (file.validate() && !(FilenameUtils.getExtension(file.name) in Constants.INVALID_ATTACHMENT_EXTENSIONS) && (mpFile.size <= Constants.MAX_ATTACHMENT_SIZE)) {
                        note.file = file
                    } else {
                        note.file = null
                        request.setAttribute("errorObject", file)
                        fileOk = false
                    }
                }
            }
        }
        return fileOk
    }

    @Secured(["ROLE_AUTHENTICATED"])
    def removeNoteFile(noteId) {
        Note note

        if (noteId) {
            Note.withNewSession {
                note = Note.get(noteId)

                if (note?.file) {
                    note.file = new NoteFile()
                    note = note.merge(flush: true)
                }
            }
        }
        return note
    }
}
