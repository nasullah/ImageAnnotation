package imageannotation

class AnnotationTool {

    static auditable = true
    static belongsTo = [annotationStep:AnnotationStep]
    static constraints = {
        annotationToolName()
    }

    String annotationToolName

    /*
 * Methods of the Domain Class
 */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Annotation Tool Name: ${annotationToolName}";
    }
}
