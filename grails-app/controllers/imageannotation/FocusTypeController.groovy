package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.context.ServletContextHolder

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class FocusTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FocusType.list(params), model:[focusTypeCount: FocusType.count()]
    }

    def show(FocusType focusType) {
        respond focusType
    }

    def create() {
        respond new FocusType(params)
    }

    @Transactional
    def save(FocusType focusType) {
        if (focusType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusType.errors, view:'create'
            return
        }

        focusType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'focusType.label', default: 'FocusType'), focusType.id])
                redirect focusType
            }
            '*' { respond focusType, [status: CREATED] }
        }
    }

    def edit(FocusType focusType) {
        respond focusType
    }

    @Transactional
    def update(FocusType focusType) {
        if (focusType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusType.errors, view:'edit'
            return
        }

        focusType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'focusType.label', default: 'FocusType'), focusType.id])
                redirect focusType
            }
            '*'{ respond focusType, [status: OK] }
        }
    }

    @Transactional
    def delete(FocusType focusType) {

        if (focusType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        focusType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'focusType.label', default: 'FocusType'), focusType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'focusType.label', default: 'FocusType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
