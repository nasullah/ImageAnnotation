package imageannotation

class AnnotationTool {

    static belongsTo = [annotationStep:AnnotationStep]
    static constraints = {
        annotationToolName()
    }

    String annotationToolName
}
