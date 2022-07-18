/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.construction.controller

import com.bionova.optimi.core.domain.mongo.Note
import com.bionova.optimi.core.domain.mongo.NoteFile

/**
 * @author Pasi-Markus Mäkelä
 */
class NoteController extends AbstractDomainObjectController {

    def noteService
    def entityService

    def form() {
        Note note
        def model = [entity: entityService.getEntityById(params.entityId, session), type: ("attachment".equals(params.type) ? 'attachment' : 'note')]

        if (params.id) {
            note = noteService.getNoteById(params.id)
            model.put("note", note)
        }
        return model
    }

    @Override
    def list() {
        // do nothing
    }

    @Override
    def saveInternal() {
        Note note

        if (params.id) {
            note = noteService.getNoteById(params.id)
            note.properties = params
        } else {
            note = new Note(params)
        }
        def entityId = params.entityId

        if (note.id) {
            boolean updateOk = noteService.updateNote(note, request)

            if (updateOk) {
                if ("attachment".equals(note.type)) {
                    flash.fadeSuccessAlert = message(code: "attachment.modify_successful")
                } else {
                    flash.fadeSuccessAlert = message(code: "note.modify_successful")
                }
                redirect controller: "entity", action: "show", id: entityId, params: [activeTab: ("attachment".equals(note.type) || note.file ? 'attachments' : 'notes')]
            } else {
                flash.fadeErrorAlert = renderErrors(bean: note)
                chain action: "form", model: [note: note], params: [entityId: entityId, type: note?.type]
            }
        } else {
            note = noteService.addNoteToEntity(entityId, note, request)

            if (note.validate()) {
                if ("attachment".equals(note.type)) {
                    flash.fadeSuccessAlert = message(code: "attachment.add_successful")
                } else {
                    flash.fadeSuccessAlert = message(code: "note.add_successful")
                }
                redirect controller: "entity", action: "show", id: entityId, params: [activeTab: ("attachment".equals(note.type) || note.file ? 'attachments' : 'notes')]
            } else {
                def errors = renderErrors(bean: note)
                flash.fadeErrorAlert = errors
                chain action: "form", model: [note: note], params: [entityId: entityId, type: note.type]
            }
        }
    }

    def remove() {
        def entityId = params.entityId
        def noteId = params.id
        noteService.removeNote(entityId, noteId)
        String type = params.type

        if ("attachment".equals(type)) {
            flash.fadeSuccessAlert = message(code: "attachment.remove_successful")
        } else {
            flash.fadeSuccessAlert = message(code: "note.remove_successful")
        }
        redirect controller: "entity", action: "show", id: entityId, params: [activeTab: ("attachment".equals(type) ? 'attachments' : 'notes')]
    }

    def removeFile() {
        def entityId = params.entityId
        def noteId = params.id
        def type = params.type
        noteService.removeNoteFile(noteId)
        flash.fadeSuccessAlert = message(code: "note.file.remove_successful")
        redirect action: "form", id: noteId, params: [entityId: params.entityId, type: type]
    }

    def downloadFile() {
        String noteId = params.id
        NoteFile file

        if (noteId) {
            file = Note.get(noteId)?.file
        }

        if (file) {
            response.setContentType(file.contentType)
            response.setHeader("Content-disposition", "attachment; filename=${file.name}")
            response.setHeader("X-Content-Type-Options", "nosniff")
            response.outputStream << file.data
        } else {
            render text: "File not found"
        }
    }
}
