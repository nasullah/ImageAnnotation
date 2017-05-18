package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ImageTypeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ImageType.list(params), model:[imageTypeCount: ImageType.count()]
    }

    def show(ImageType imageType) {
        respond imageType
    }

    def create() {
        respond new ImageType(params)
    }

    @Transactional
    def save(ImageType imageType) {
        if (imageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (imageType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond imageType.errors, view:'create'
            return
        }

        imageType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'imageType.label', default: 'ImageType'), imageType.id])
                redirect imageType
            }
            '*' { respond imageType, [status: CREATED] }
        }
    }

    def edit(ImageType imageType) {
        respond imageType
    }

    @Transactional
    def update(ImageType imageType) {
        if (imageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (imageType.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond imageType.errors, view:'edit'
            return
        }

        imageType.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'imageType.label', default: 'ImageType'), imageType.id])
                redirect imageType
            }
            '*'{ respond imageType, [status: OK] }
        }
    }

    @Transactional
    def delete(ImageType imageType) {

        if (imageType == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        imageType.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'imageType.label', default: 'ImageType'), imageType.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'imageType.label', default: 'ImageType'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
