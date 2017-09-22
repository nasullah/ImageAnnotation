package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AnnotationStepController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AnnotationStep.list(params), model:[annotationStepCount: AnnotationStep.count()]
    }

    def show(AnnotationStep annotationStep) {
        respond annotationStep
    }

    def create() {
        respond new AnnotationStep(params)
    }

    @Transactional
    def save(AnnotationStep annotationStep) {
        if (annotationStep == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationStep.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationStep.errors, view:'create'
            return
        }

        annotationStep.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annotationStep.label', default: 'AnnotationStep'), annotationStep.id])
//                redirect annotationStep
                redirect(controller:'annotationTask',action: 'show', params: [id: annotationStep.annotationTask.id])
            }
            '*' { respond annotationStep, [status: CREATED] }
        }
    }

    def edit(AnnotationStep annotationStep) {
        respond annotationStep
    }

    @Transactional
    def update(AnnotationStep annotationStep) {
        if (annotationStep == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationStep.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationStep.errors, view:'edit'
            return
        }

        annotationStep.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annotationStep.label', default: 'AnnotationStep'), annotationStep.id])
//                redirect annotationStep
                redirect(controller:'annotationTask',action: 'show', params: [id: annotationStep.annotationTask.id])
            }
            '*'{ respond annotationStep, [status: OK] }
        }
    }

    @Transactional
    def delete(AnnotationStep annotationStep) {

        if (annotationStep == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        annotationStep.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annotationStep.label', default: 'AnnotationStep'), annotationStep.id])
//                redirect action:"index", method:"GET"
                redirect(controller:'annotationTask',action: 'show', params: [id: annotationStep.annotationTask.id])
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annotationStep.label', default: 'AnnotationStep'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
