package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class StudyTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond StudyType.list(params), model:[studyTypeCount: StudyType.count()]
    }

    def show(StudyType studyType) {
        respond studyType
    }

    def create() {
        respond new StudyType(params)
    }

    @Transactional
    def save(StudyType studyType) {
        if (studyType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (studyType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond studyType.errors, view:'create'
            return
        }

        studyType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'studyType.label', default: 'StudyType'), studyType.id])
                redirect studyType
            }
            '*' { respond studyType, [status: CREATED] }
        }
    }

    def edit(StudyType studyType) {
        respond studyType
    }

    @Transactional
    def update(StudyType studyType) {
        if (studyType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (studyType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond studyType.errors, view:'edit'
            return
        }

        studyType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'studyType.label', default: 'StudyType'), studyType.id])
                redirect studyType
            }
            '*'{ respond studyType, [status: OK] }
        }
    }

    @Transactional
    def delete(StudyType studyType) {

        if (studyType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        studyType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'studyType.label', default: 'StudyType'), studyType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'studyType.label', default: 'StudyType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
