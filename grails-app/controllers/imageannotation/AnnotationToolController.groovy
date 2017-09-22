package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class AnnotationToolController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AnnotationTool.list(params), model:[annotationToolCount: AnnotationTool.count()]
    }

    def show(AnnotationTool annotationTool) {
        respond annotationTool
    }

    def create() {
        respond new AnnotationTool(params)
    }

    @Transactional
    def save(AnnotationTool annotationTool) {
        if (annotationTool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationTool.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationTool.errors, view:'create'
            return
        }

        annotationTool.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annotationTool.label', default: 'AnnotationTool'), annotationTool.id])
//                redirect annotationTool
                redirect(controller:'annotationStep',action: 'show', params: [id: annotationTool.annotationStep.id])
            }
            '*' { respond annotationTool, [status: CREATED] }
        }
    }

    def edit(AnnotationTool annotationTool) {
        respond annotationTool
    }

    @Transactional
    def update(AnnotationTool annotationTool) {
        if (annotationTool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (annotationTool.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond annotationTool.errors, view:'edit'
            return
        }

        annotationTool.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annotationTool.label', default: 'AnnotationTool'), annotationTool.id])
//                redirect annotationTool
                redirect(controller:'annotationStep',action: 'show', params: [id: annotationTool.annotationStep.id])
            }
            '*'{ respond annotationTool, [status: OK] }
        }
    }

    @Transactional
    def delete(AnnotationTool annotationTool) {

        if (annotationTool == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        annotationTool.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annotationTool.label', default: 'AnnotationTool'), annotationTool.id])
//                redirect action:"index", method:"GET"
                redirect(controller:'annotationStep',action: 'show', params: [id: annotationTool.annotationStep.id])
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annotationTool.label', default: 'AnnotationTool'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
