package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.context.ServletContextHolder

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class StainTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond StainType.list(params), model:[stainTypeCount: StainType.count()]
    }

    def show(StainType stainType) {
        respond stainType
    }

    def create() {
        respond new StainType(params)
    }

    @Transactional
    def save(StainType stainType) {
        if (stainType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stainType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stainType.errors, view:'create'
            return
        }

        stainType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'stainType.label', default: 'StainType'), stainType.id])
                redirect stainType
            }
            '*' { respond stainType, [status: CREATED] }
        }
    }

    def edit(StainType stainType) {
        respond stainType
    }

    @Transactional
    def update(StainType stainType) {
        if (stainType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (stainType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond stainType.errors, view:'edit'
            return
        }

        stainType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'stainType.label', default: 'StainType'), stainType.id])
                redirect stainType
            }
            '*'{ respond stainType, [status: OK] }
        }
    }

    @Transactional
    def delete(StainType stainType) {

        if (stainType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        stainType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'stainType.label', default: 'StainType'), stainType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'stainType.label', default: 'StainType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
