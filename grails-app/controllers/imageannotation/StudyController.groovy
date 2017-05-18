package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StudyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Study.list(params), model:[studyCount: Study.count()]
    }

    def show(Study study) {
        respond study
    }

    def create() {
        respond new Study(params)
    }

    @Transactional
    def save(Study study) {
        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (study.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond study.errors, view:'create'
            return
        }

        study.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect study
            }
            '*' { respond study, [status: CREATED] }
        }
    }

    def edit(Study study) {
        respond study
    }

    @Transactional
    def update(Study study) {
        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (study.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond study.errors, view:'edit'
            return
        }

        study.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect study
            }
            '*'{ respond study, [status: OK] }
        }
    }

    @Transactional
    def delete(Study study) {

        if (study == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        study.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'study.label', default: 'Study'), study.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'study.label', default: 'Study'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
