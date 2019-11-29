package imageannotation

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.web.context.ServletContextHolder

@Secured(['ROLE_USER', 'ROLE_ADMIN'])
@Transactional(readOnly = true)
class MultiplexImageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def exportService
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

    def exportAnnotations(){
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= Exported annnotations.${params.extension}")
            def study = Study.findByStudyName(params.study)
            def expert = Expert.findById(params.long('annotator'))
            def annotations = []
            if(expert){
                def multiplexImages = Annotation.findAllByImageAnnotatorAndMultiplexImageInList(expert, study?.multiplexImages).multiplexImage.unique()
                for (MultiplexImage image : multiplexImages) {
                    def latestAnnotation = Annotation.findAllByMultiplexImageAndImageAnnotator(image, expert).sort{it.id}.last()
                    annotations.add(latestAnnotation)
                }
            }
            List fields = ["multiplexImage.multiplexImageIdentifier", "annotationData", "imageAnnotator"]
            Map labels = ["multiplexImage.multiplexImageIdentifier":"Image", "annotationData":"Annotation", "imageAnnotator":"Annotator"]
            Map formatters = [:]
            Map parameters = [title: "Annotation"]
            exportService.export(params.extension, response.outputStream, annotations, fields, labels, formatters, parameters )
        }
    }

    def exportAnnotationPerStudy(){
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= Exported annnotations.${params.extension}")
            def study = Study.findByStudyName(params.study)
            def annotations = []
                def multiplexImages = Annotation.findAllByMultiplexImageInList(study?.multiplexImages).multiplexImage.unique()
                for (MultiplexImage image : multiplexImages) {
                    def latestAnnotation = Annotation.findAllByMultiplexImage(image).sort{it.id}.last()
                    annotations.add(latestAnnotation)
                }
            List fields = ["multiplexImage.multiplexImageIdentifier", "annotationData", "imageAnnotator"]
            Map labels = ["multiplexImage.multiplexImageIdentifier":"Image", "annotationData":"Annotation", "imageAnnotator":"Annotator"]
            Map formatters = [:]
            Map parameters = [title: "Annotation"]
            exportService.export(params.extension, response.outputStream, annotations, fields, labels, formatters, parameters )
        }
    }

    def exportIHCData(){
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= Exported annnotations.${params.extension}")
            def study = Study.findByStudyName(params.study)
            def expert = Expert.findById(params.long('annotator'))
            def focusData = []
            if(expert){
                def multiplexImages = MultiplexImage.findAllByStudy(study)
                focusData = FocusStatus.findAllByExpertAndMultiplexImageInList(expert, multiplexImages).sort{it.multiplexImage}
            }
            List fields = ["multiplexImage.multiplexImageIdentifier", "multiplexImage.multiplexImageType", "focusNumber", "focusType", "diagnosis", "stainType", "stainTypeNameOther", "diagnosisNameOther", "multiplexImage.comment", "expert"]
            Map labels = ["multiplexImage.multiplexImageIdentifier":"Image", "diagnosis":"Diagnosis", "diagnosisNameOther":"Diagnosis other","multiplexImage.multiplexImageType":"Case type", "focusType": "Benign/Malegnant", "stainType":"Staining code", "stainTypeNameOther":"Staining code other", "expert":"Annotator", "focusNumber":"Focus number", "multiplexImage.comment":"Comment"]
            Map formatters = [:]
            Map parameters = [title: "Annotation"]
            exportService.export(params.extension, response.outputStream, focusData, fields, labels, formatters, parameters )
        }
    }

    def exportIHCDataPerStudy(){
        if(params?.extension && params?.extension != "html"){
            response.contentType = grailsApplication.config.grails.mime.types[params?.extension]
            response.setHeader("Content-disposition", "attachment; filename= Exported annnotations.${params.extension}")
            def study = Study.findByStudyName(params.study)
            def focusData = []
            def multiplexImages = MultiplexImage.findAllByStudy(study)
            focusData = FocusStatus.findAllByMultiplexImageInList(multiplexImages).sort{it.multiplexImage}
            List fields = ["multiplexImage.multiplexImageIdentifier", "multiplexImage.multiplexImageType", "focusNumber", "focusType", "diagnosis", "stainType", "stainTypeNameOther", "diagnosisNameOther", "multiplexImage.comment", "expert"]
            Map labels = ["multiplexImage.multiplexImageIdentifier":"Image", "diagnosis":"Diagnosis", "diagnosisNameOther":"Diagnosis other","multiplexImage.multiplexImageType":"Case type", "focusType": "Benign/Malegnant", "stainType":"Staining code", "stainTypeNameOther":"Staining code other", "expert":"Annotator", "focusNumber":"Focus number", "multiplexImage.comment":"Comment"]
            Map formatters = [:]
            Map parameters = [title: "Annotation"]
            exportService.export(params.extension, response.outputStream, focusData, fields, labels, formatters, parameters )
        }
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
                if (study?.studyName == 'Megakaryocyte_Prediction_Validation'){
                    def imageList = Assignment.findAllByExpert(expert)?.multiplexImage
                    [imageList: imageList, annotatorId:expert.id]
                }else {
                    def imageList = Annotation.findAllByImageAnnotatorAndMultiplexImageInList(expert, study?.multiplexImages).multiplexImage
                    [imageList: imageList.sort{it.multiplexImageIdentifier}?.unique(), annotatorId:expert?.id, study:study?.id]
                }
            }
        }
    }

    def sharedImageList(){
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
                if (study?.studyName == 'TCGA_Prostate_Study'){
                    def imageList = Annotation.findAllByImageAnnotatorAndMultiplexImageInList(Expert.findById(250), study?.multiplexImages).multiplexImage
                    [imageList: imageList?.unique(), annotatorId:expert.id]
                }else if (params.suffix){
                    def imageList = MultiplexImage.findAllByStudy(Study.findByStudyName(params.study))
                    imageList = imageList.findAll {it.multiplexImageIdentifier.endsWith(params.suffix)}
                    [imageList: imageList?.sort{it?.study?.studyName}, annotatorId:expert.id]
                }else {
                    def imageList = MultiplexImage.findAllByStudy(Study.findByStudyName(params.study))
                    [imageList: imageList?.sort{it?.study?.studyName}, annotatorId:expert.id]
                }
            }
        }
    }

    @Secured('ROLE_ADMIN')
    def loadImages(){
    }

    @Secured('ROLE_ADMIN')
    @Transactional
    def saveLoadedImages(){
        def folderName = params.folderName
        def studyName = params.studyName
        def realPath = 'assets/attachments/' + folderName
        def dirPath = ServletContextHolder.servletContext.getRealPath(realPath)
        def dir = new File(dirPath).list()
        dir.each {
            item ->
                def imageName = null
                if (item.toString().contains('.png') || item.toString().contains('.dzi') || item.toString().contains('.jpg')){
                    imageName = item.toString().replace('.png','').replace('.dzi','').replace('.jpg','')
                    if(!MultiplexImage.findByMultiplexImageName(imageName)){
                        def multiplexImage = new MultiplexImage()
                        multiplexImage.multiplexImageName = imageName
                        multiplexImage.multiplexImageIdentifier = imageName
                        multiplexImage.multiplexImageType = MultiplexImageType.findByMultiplexImageTypeName('Real')
                        multiplexImage.study = Study.findByStudyName(studyName)
                        multiplexImage.save failOnError: true
                        def pathologyImage = new PathologyImage()
                        pathologyImage.imageIdentifier = imageName
                        pathologyImage.imagePath = '../' + realPath + '/' + item
                        pathologyImage.imageType = ImageType.findByImageTypeName('Simple')
                        multiplexImage.addToChannels(pathologyImage).save failOnError: true
                    }
                }
        }
        redirect action: "index", method: "GET"
    }

    @Secured('ROLE_ADMIN')
    def configInterface(){
    }

    @Secured('ROLE_ADMIN')
    @Transactional
    def saveConfiguration(){
        def folderName = params.folderName
        def folderPath = 'assets/attachments/' + folderName
        def folder = ServletContextHolder.servletContext.getRealPath(folderPath)
        def folderItems = new File(folder).list()
        def multiplexImageNameList = []
        folderItems.each {item -> multiplexImageNameList.add(item.toString().replace('.png','').replace('.dzi','').replace('.jpg',''))}
        def multiplexImageList = MultiplexImage.findAllByMultiplexImageNameInList(multiplexImageNameList)
        multiplexImageList.each {multiplexImage ->
            if(multiplexImage){
                def annotation = new Annotation()
                def realPath = multiplexImage?.channels[0]?.imagePath
                def imageName = multiplexImage?.multiplexImageName
                def annotationData = params.annotationData
                annotationData = annotationData?.toString()?.replace('images_realPath',realPath)?.replace('images_realName',imageName)
                annotation.annotationData = annotationData
                annotation.imageAnnotator = Expert.findById(params.long('annotator'))
                multiplexImage.addToAnnotations(annotation).save failOnError: true
            }
        }
        redirect action: "index", method: "GET"
    }

    @Secured('ROLE_ADMIN')
    @Transactional
    def saveConfigurationChannel(){
        def folderName = params.folderName
        def folderPath = 'assets/attachments/' + folderName
        def folder = ServletContextHolder.servletContext.getRealPath(folderPath)
        def folderItems = new File(folder).list()
        def multiplexImageNameList = []
        folderItems.each {
            item ->
                if (item.toString().contains('.png') || item.toString().contains('.dzi') || item.toString().contains('.jpg')){
                    multiplexImageNameList.add(item.toString().replace('.png','').replace('.dzi','').replace('.jpg',''))
                }
        }
        def multiplexImageList = MultiplexImage.findAllByMultiplexImageNameInList(multiplexImageNameList)
        multiplexImageList.each {multiplexImage ->
            if(multiplexImage){
                def annotation = new Annotation()
                def realPath = '../' + folderPath + '/' + multiplexImage?.multiplexImageName + '/'
                def imageName = multiplexImage?.multiplexImageName
                def annotationData = params.annotationData
                annotationData = annotationData?.toString()?.replace('images_realPath',realPath)?.replace('images_realName',imageName)
                annotation.annotationData = annotationData
                annotation.imageAnnotator = Expert.findById(params.long('annotator'))
                multiplexImage.addToAnnotations(annotation).save failOnError: true
            }
        }
        redirect action: "index", method: "GET"
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
