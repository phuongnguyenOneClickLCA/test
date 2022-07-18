package com.bionova.optimi.core.service

import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

/**
 * @author Luke Driscoll
 */

class MaskingService {
    private static final char MASK = '*' as char
    FlashService flashService

    /**
     * Mask an email address.
     * Will keep the @domain and mask all personal data, except for the first 4 characters
     * e.g luke*****@company.com
     *
     * Default masking character: "*"
     *
     * @param strEmail the email address to mask
     * @return the masked email address
     */
    def maskEmail(String strEmail) {

        return maskEmail(strEmail, MASK)
    }

    /**
     * Mask an email address.
     * Will keep the @domain and mask all personal data, except for the first 4 characters
     * e.g luke*****@company.com
     *
     * @param strEmail the email address to mask
     * @param maskChar the character used to mask the information
     * @return the masked email address
     */
    def maskEmail(String strEmail, char maskChar) {

        return maskEmail(strEmail, maskChar, 8, 0)
    }

    /**
     * Mask an email address.
     * Will keep the @domain and mask all personal data, except for for the start/end offset value
     * e.g lu*****ll@company.com
     *
     * Default masking character: "*"
     *
     * @param strEmail the email address to mask
     * @param startOffset the amount of characters to reveal at the start of the email
     * @param endOffset the amount of characters to reveal at the end of the email
     * @return the masked email address
     */
    def maskEmail(String strEmail, int startOffset, int endOffset ) {
        return maskEmail(strEmail, MASK, startOffset, endOffset)
    }

    /**
     * Mask an email address.
     * Will keep the @domain and mask all personal data, except for for the start/end offset value
     * e.g lu*****ll@company.com
     *
     * @param strEmail the email address to mask
     * @param maskChar the character used to mask the information
     * @param startOffset the amount of characters to reveal at the start of the email
     * @param endOffset the amount of characters to reveal at the end of the email
     * @return the masked email address
     */
    def maskEmail(String strEmail, char maskChar, int startOffset, int endOffset) {

        if(!isValidEmail(strEmail)) {
            if(strEmail && strEmail.length() > startOffset) {
                return maskString(strEmail, startOffset, strEmail.length() + endOffset, MASK)
            } else {
                return strEmail
            }
        }

        //Splits the email into an array from the @ symbol
        String[] parts = strEmail?.split("@")

        //mask first part
        String strId = ""

        if (parts) {
            int length = parts[0].length()

            if(length < startOffset) {
                //if part is less than 8
                strId = maskString(parts[0], (length / 2).round().intValue(), length + endOffset, maskChar)
            } else {
                strId = maskString(parts[0], startOffset, length + endOffset, maskChar)
            }
            //now append the domain part to the masked id part
            return strId + "@" + parts[1]
        } else {
            return strId
        }
    }

    boolean isValidEmail(String email) {
        boolean isValid = Boolean.TRUE.booleanValue()
        if(!email) {
            log.warn("${email} is not a valid adress.")
            flashService.setWarningAlert("${email} is not a valid adress.", true)
            isValid = Boolean.FALSE.booleanValue()
        }
        try {
            InternetAddress address = new InternetAddress(email)
            address.validate()
        } catch (AddressException ex) {
            log.warn("${email} is not a valid adress: ${ex.message}")
            isValid = Boolean.FALSE.booleanValue()
        }
        return isValid
    }

    /**
     * Mask a users full name.
     * Will mask the users full name
     * e.g **** *******
     *
     * @param strFullName the full name to mask
     * @return the masked full name
     */
    def maskFullName(String strFullName) {

        //mask full name
        String strId = maskString(strFullName, 0, strFullName.length(), MASK)

        //now append the domain part to the masked id part
        return strId
    }

    /**
     * Mask a users Surname.
     * Will keep everything before a space (" ") and mask any text afterwards
     * e.g Luke *******
     *
     * @param strFullName the full name to split and mask
     * @return the first name and masked surname
     */
    def maskSurname(String strFullName) {

        if (strFullName) {
            if (strFullName.contains(" ") && strFullName.trim().size() > 0) {
                String[] parts = strFullName.split(" ")
                String strId = maskString(parts[1], 0, parts[1].length(), MASK)
                return parts[0] + " " + strId
            } else {
                int offset = Math.round(strFullName.length() / 2)
                return maskString(strFullName, offset, strFullName.length(), MASK)
            }
        } else {
            return ""
        }
    }

    /**
     * Mask a string
     * Will mask a string with the provided offsets/character
     *
     * @param strText the string that is being masked
     * @param start the starting offset value
     * @param end the ending offset value
     * @param maskChar the character being used to mask
     * @return the first name and masked surname
     */
    def maskString(String strText, int start, int end, char maskChar) {

        if (strText == null || strText.equals("")) {
            return ""
        }

        if (start < 0) {
            start = 0
        }

        if (end > strText.length()) {
            end = strText.length()
        }

        if (start > end) {
            throw new Exception("End index cannot be greater than start index")
        }

        int maskLength = end - start

        if (maskLength == 0) {
            return strText
        }

        StringBuilder sbMaskString = new StringBuilder(maskLength)

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar)
        }

        return strText.substring(0, start) + sbMaskString.toString() + strText.substring(start + maskLength)


    }
}
