package imageannotation

class AnnotationStep {

    static auditable = true
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

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotation Step Number: ${annotationStepNumber}";
    }
}
