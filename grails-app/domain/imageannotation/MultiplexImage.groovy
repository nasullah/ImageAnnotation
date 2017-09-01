package imageannotation

class MultiplexImage {

    static auditable = true
    static belongsTo = [study:Study]
    static hasMany = [pathologyImages:PathologyImage]
    static constraints = {
        study()
        multiplexImageIdentifier()
        multiplexImagePath()
    }

    String multiplexImageIdentifier
    String multiplexImagePath

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Study: ${study}, Image Identifier: ${multiplexImageIdentifier}";
    }
}
