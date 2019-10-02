package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class FocusStatusDescController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FocusStatusDesc.list(params), model:[focusStatusDescCount: FocusStatusDesc.count()]
    }

    def show(FocusStatusDesc focusStatusDesc) {
        respond focusStatusDesc
    }

    def create() {
        respond new FocusStatusDesc(params)
    }

    @Transactional
    def save(FocusStatusDesc focusStatusDesc) {
        if (focusStatusDesc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatusDesc.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatusDesc.errors, view:'create'
            return
        }

        focusStatusDesc.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'focusStatusDesc.label', default: 'FocusStatusDesc'), focusStatusDesc.id])
                redirect focusStatusDesc
            }
            '*' { respond focusStatusDesc, [status: CREATED] }
        }
    }

    def edit(FocusStatusDesc focusStatusDesc) {
        respond focusStatusDesc
    }

    @Transactional
    def update(FocusStatusDesc focusStatusDesc) {
        if (focusStatusDesc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (focusStatusDesc.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond focusStatusDesc.errors, view:'edit'
            return
        }

        focusStatusDesc.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'focusStatusDesc.label', default: 'FocusStatusDesc'), focusStatusDesc.id])
                redirect focusStatusDesc
            }
            '*'{ respond focusStatusDesc, [status: OK] }
        }
    }

    @Transactional
    def delete(FocusStatusDesc focusStatusDesc) {

        if (focusStatusDesc == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        focusStatusDesc.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'focusStatusDesc.label', default: 'FocusStatusDesc'), focusStatusDesc.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'focusStatusDesc.label', default: 'FocusStatusDesc'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
