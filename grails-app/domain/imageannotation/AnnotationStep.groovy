package imageannotation

class AnnotationStep {

    static mapping = {
        instruction type: "text"
    }
    static belongsTo = [annotationTask:AnnotationTask]
    static hasMany = [annotationTools:AnnotationTool]
    static constraints = {
        annotationStepNumber()
        instruction()
        region(nullable: true)
    }

    String instruction
    Integer annotationStepNumber
    String region

}
