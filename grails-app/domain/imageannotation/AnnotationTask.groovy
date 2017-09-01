package imageannotation

class AnnotationTask {

    static auditable = true
    static hasMany = [annotationSteps:AnnotationStep]
    static constraints = {
        annotationTaskName()
        annotationTaskDesc(nullable: true)
    }

    String annotationTaskName
    String annotationTaskDesc

    /*
 * Methods of the Domain Class
 */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotation Task Name: ${annotationTaskName}";
    }
}
