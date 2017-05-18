package imageannotation

class Annotation {

    static mapping = {
        annotationData type: "text"
    }

    static belongsTo = [pathologyImage:PathologyImage]
    static constraints = {
        pathologyImage()
        annotationData()
        imageAnnotator()
    }

    String annotationData
    Expert imageAnnotator

}
