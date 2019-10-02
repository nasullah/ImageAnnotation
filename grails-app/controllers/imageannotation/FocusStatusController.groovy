package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class FocusStatusController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FocusStatus.list(params), model:[focusStatusCount: FocusStatus.count()]
    }

    def show(FocusStatus focusStatus) {
        respond focusStatus
    }

    def create() {
        respond new FocusStatus(params)
    }

    @Transactional
    def save(FocusStatus focusStatus) {
        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatus.errors, view:'create'
            return
        }

        focusStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect focusStatus
            }
            '*' { respond focusStatus, [status: CREATED] }
        }
    }

    def edit(FocusStatus focusStatus) {
        respond focusStatus
    }

    @Transactional
    def update(FocusStatus focusStatus) {
        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatus.errors, view:'edit'
            return
        }

        focusStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect focusStatus
            }
            '*'{ respond focusStatus, [status: OK] }
        }
    }

    @Transactional
    def delete(FocusStatus focusStatus) {

        if (focusStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        focusStatus.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), focusStatus.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'focusStatus.label', default: 'FocusStatus'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
