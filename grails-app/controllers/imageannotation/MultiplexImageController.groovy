package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class MultiplexImageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured('ROLE_ADMIN')
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MultiplexImage.list(params), model:[multiplexImageCount: MultiplexImage.count()]
    }

    def show(MultiplexImage multiplexImage) {
        respond multiplexImage
    }

    def create() {
        respond new MultiplexImage(params)
    }

    def yourImageList(){
        def study = Study.findByStudyName(params.study)
        def currentUser = springSecurityService.currentUser.username
        if (currentUser){
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and{
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }
            if(expert){
                def imageList = Annotation.findAllByImageAnnotator(expert).multiplexImage
                imageList = imageList.findAll {it?.study?.studyType?.studyTypeName != 'Shared'}
                imageList = imageList.findAll {it.study == study}
                [imageList: imageList?.unique()?.sort{it?.study?.studyName}, annotatorId:expert.id]
            }
        }
    }

    def sharedImageList(){
        def currentUser = springSecurityService.currentUser.username
        if (currentUser){
            def forename = currentUser?.toString()?.split("\\.")[0]
            def surname = currentUser?.toString()?.split("\\.")[1]
            def expert = Expert.createCriteria().get {
                and{
                    eq("givenName", forename, [ignoreCase: true])
                    eq("familyName", surname, [ignoreCase: true])
                }
            }
            if(expert){
                def imageList = MultiplexImage.findAllByStudy(Study.findByStudyName(params.study))
                [imageList: imageList?.sort{it?.study?.studyName}, annotatorId:expert.id]
            }
        }
    }

    @Transactional
    def save(MultiplexImage multiplexImage) {
        if (multiplexImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (multiplexImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multiplexImage.errors, view:'create'
            return
        }

        multiplexImage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'multiplexImage.label', default: 'MultiplexImage'), multiplexImage.id])
//                redirect multiplexImage
                redirect(controller:'study',action: 'show', params: [id: multiplexImage.study.id])
            }
            '*' { respond multiplexImage, [status: CREATED] }
        }
    }

    def edit(MultiplexImage multiplexImage) {
        respond multiplexImage
    }

    @Transactional
    def update(MultiplexImage multiplexImage) {
        if (multiplexImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (multiplexImage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond multiplexImage.errors, view:'edit'
            return
        }

        multiplexImage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'multiplexImage.label', default: 'MultiplexImage'), multiplexImage.id])
//                redirect multiplexImage
                redirect(controller:'study',action: 'show', params: [id: multiplexImage.study.id])
            }
            '*'{ respond multiplexImage, [status: OK] }
        }
    }

    @Secured('ROLE_ADMIN')
    @Transactional
    def delete(MultiplexImage multiplexImage) {

        if (multiplexImage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        multiplexImage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'multiplexImage.label', default: 'MultiplexImage'), multiplexImage.id])
//                redirect action:"index", method:"GET"
                redirect(controller:'study',action: 'show', params: [id: multiplexImage.study.id])
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'multiplexImage.label', default: 'MultiplexImage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
