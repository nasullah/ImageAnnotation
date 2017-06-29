package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AnnotationTaskController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AnnotationTask.list(params), model:[annotationTaskCount: AnnotationTask.count()]
    }

    def show(AnnotationTask annotationTask) {
        respond annotationTask
    }

    def create() {
        respond new AnnotationTask(params)
    }

    @Transactional
    def save(AnnotationTask annotationTask) {
        if (annotationTask == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationTask.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationTask.errors, view:'create'
            return
        }

        annotationTask.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annotationTask.label', default: 'AnnotationTask'), annotationTask.id])
                redirect annotationTask
            }
            '*' { respond annotationTask, [status: CREATED] }
        }
    }

    def edit(AnnotationTask annotationTask) {
        respond annotationTask
    }

    @Transactional
    def update(AnnotationTask annotationTask) {
        if (annotationTask == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationTask.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationTask.errors, view:'edit'
            return
        }

        annotationTask.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annotationTask.label', default: 'AnnotationTask'), annotationTask.id])
                redirect annotationTask
            }
            '*'{ respond annotationTask, [status: OK] }
        }
    }

    @Transactional
    def delete(AnnotationTask annotationTask) {

        if (annotationTask == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        annotationTask.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annotationTask.label', default: 'AnnotationTask'), annotationTask.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annotationTask.label', default: 'AnnotationTask'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
