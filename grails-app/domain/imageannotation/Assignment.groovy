package imageannotation

class Assignment {

    static auditable = true
    static belongsTo = [multiplexImage:MultiplexImage]
    static constraints = {
        multiplexImage()
        expert()
    }

    Expert expert

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "Expert: ${expert}";
    }
}
