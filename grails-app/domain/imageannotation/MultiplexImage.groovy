package imageannotation

class MultiplexImage {

    static auditable = true
    static mapping = {
        comment type: "text"
    }
    static belongsTo = [study:Study]
    static hasMany = [channels:PathologyImage, annotations:Annotation, assignments:Assignment, foci:FocusStatus]
    static constraints = {
        study()
        multiplexImageIdentifier()
        multiplexImageName()
        multiplexImageDesc(nullable: true)
        comment(nullable: true)
    }

    String multiplexImageIdentifier
    String multiplexImageName
    String multiplexImageDesc
    String comment

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Study: ${study}, Image Identifier: ${multiplexImageIdentifier}";
    }
}
