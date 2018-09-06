package imageannotation

class MultiplexImage {

    static auditable = true
    static belongsTo = [study:Study]
    static hasMany = [channels:PathologyImage, annotations:Annotation, assignments:Assignment]
    static constraints = {
        study()
        multiplexImageIdentifier()
        multiplexImageName()
        multiplexImageDesc(nullable: true)
    }

    String multiplexImageIdentifier
    String multiplexImageName
    String multiplexImageDesc

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Study: ${study}, Image Identifier: ${multiplexImageIdentifier}";
    }
}
