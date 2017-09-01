package imageannotation

import grails.converters.JSON
import grails.web.context.ServletContextHolder

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class PathologyImageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PathologyImage.list(params), model:[pathologyImageCount: PathologyImage.count()]
    }

    def show(PathologyImage pathologyImage) {
        respond pathologyImage
    }

    def create() {
        respond new PathologyImage(params)
    }

    def unAnnotatedImages(){
        def unAnnotatedPathologyImages = PathologyImage.list()
        unAnnotatedPathologyImages = unAnnotatedPathologyImages.findAll {pathologyImage -> !Annotation.findByPathologyImage(pathologyImage)}
        [unAnnotatedPathologyImages:unAnnotatedPathologyImages]
    }

    def annotatedImages(){
        def annotatedPathologyImages = PathologyImage.list()
        annotatedPathologyImages = annotatedPathologyImages.findAll {pathologyImage -> Annotation.findByPathologyImage(pathologyImage)}
        [annotatedPathologyImages:annotatedPathologyImages]
    }

    def viewPathologyImageOnOpenSeeDragon(){
        def path = '../assets/attachments/dzi_images/HEDZ/HEDZ.dzi'
        def data = [path:path]
        render data as JSON
    }

    @Transactional
    def save(PathologyImage pathologyImage) {
        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pathologyImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pathologyImage.errors, view:'create'
            return
        }

        pathologyImage.save flush:true

        def imageFile = request.getFile('imageFile')
        if (imageFile?.originalFilename){
            pathologyImage.imagePath = ServletContextHolder.servletContext.getRealPath('/assets/attachments') + '/' + pathologyImage.id + '.' + imageFile.originalFilename
            def destinationFile = new File(pathologyImage.imagePath)

            try {
                imageFile.transferTo(destinationFile)
                pathologyImage.save flush: true
            }
            catch (Exception ex) {
                log.error(ex)
                pathologyImage.imagePath = null
                pathologyImage.save flush: true
            }
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect pathologyImage
            }
            '*' { respond pathologyImage, [status: CREATED] }
        }
    }

    def edit(PathologyImage pathologyImage) {
        respond pathologyImage
    }

    @Transactional
    def update(PathologyImage pathologyImage) {
        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pathologyImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pathologyImage.errors, view:'edit'
            return
        }

        pathologyImage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect pathologyImage
            }
            '*'{ respond pathologyImage, [status: OK] }
        }
    }

    @Transactional
    def delete(PathologyImage pathologyImage) {

        if (pathologyImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pathologyImage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), pathologyImage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pathologyImage.label', default: 'PathologyImage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
