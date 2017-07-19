package imageannotation

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
        "/api/annotations"(resources:"annotation")
        "/api/centres"(resources:"centre")
        "/api/experts"(resources:"expert")
        "/api/imagetypes"(resources:"imageType")
        "/api/multiplexImages"(resources:"multiplexImage")
        "/api/pathologyimages"(resources:"pathologyImage")
        "/api/specialities"(resources:"speciality")
        "/api/studies"(resources:"study")
        "/api/annotationSteps"(resources:"annotationStep")
        "/api/annotationTasks"(resources:"annotationTask")
        "/api/annotationTools"(resources:"annotationTool")
    }
}
