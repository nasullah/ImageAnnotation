package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
@Transactional(readOnly = true)
class DiagnosisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Diagnosis.list(params), model:[diagnosisCount: Diagnosis.count()]
    }

    def show(Diagnosis diagnosis) {
        respond diagnosis
    }

    def create() {
        respond new Diagnosis(params)
    }

    @Transactional
    def save(Diagnosis diagnosis) {
        if (diagnosis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (diagnosis.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond diagnosis.errors, view:'create'
            return
        }

        diagnosis.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'diagnosis.label', default: 'Diagnosis'), diagnosis.id])
                redirect diagnosis
            }
            '*' { respond diagnosis, [status: CREATED] }
        }
    }

    def edit(Diagnosis diagnosis) {
        respond diagnosis
    }

    @Transactional
    def update(Diagnosis diagnosis) {
        if (diagnosis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (diagnosis.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond diagnosis.errors, view:'edit'
            return
        }

        diagnosis.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'diagnosis.label', default: 'Diagnosis'), diagnosis.id])
                redirect diagnosis
            }
            '*'{ respond diagnosis, [status: OK] }
        }
    }

    @Transactional
    def delete(Diagnosis diagnosis) {

        if (diagnosis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        diagnosis.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'diagnosis.label', default: 'Diagnosis'), diagnosis.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'diagnosis.label', default: 'Diagnosis'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
