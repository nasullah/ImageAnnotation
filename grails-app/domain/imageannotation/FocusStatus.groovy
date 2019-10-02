package imageannotation

class FocusStatus {

    static auditable = true

    static belongsTo = [multiplexImage:MultiplexImage]
    static mapping = {
        comment type: "text"
    }

    static constraints = {
        multiplexImage()
        comment(nullable: true, widget: 'textarea')
        status(nullable: true)
        focusNumber(nullable: true)
        expert(nullable: true)

    }

    String comment
    FocusStatusDesc status
    Integer focusNumber
    Expert expert

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Focus: ${status}, Id: ${id} ";
    }
}
