package imageannotation

class ImageType {

    static auditable = true
    static constraints = {
        imageTypeName()
    }

    String imageTypeName

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${imageTypeName}";
    }
}
