package imageannotation

class AnnotationTask {

    static hasMany = [annotationSteps:AnnotationStep]
    static constraints = {
        annotationTaskName()
        annotationTaskDesc(nullable: true)
    }

    String annotationTaskName
    String annotationTaskDesc
}
