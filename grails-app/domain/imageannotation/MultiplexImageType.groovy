package imageannotation

class MultiplexImageType {

    static auditable = true

    static mapping = {
        multiplexImageTypeName type: "text"
    }

    static constraints = {
        multiplexImageTypeName(nullable: true)
    }

    String multiplexImageTypeName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${multiplexImageTypeName}";
    }
}
