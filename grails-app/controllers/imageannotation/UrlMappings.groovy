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
        "/annotations"(resources:"annotation")
        "/centres"(resources:"centre")
        "/experts"(resources:"expert")
        "/imagetypes"(resources:"imageType")
        "/pathologyimages"(resources:"pathologyImage")
        "/specialities"(resources:"speciality")
        "/studies"(resources:"study")
    }
}
