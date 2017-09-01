package imageannotation

class Speciality {

    static auditable = true
    static constraints = {
        specialityName()
    }

    String specialityName

    /*
    * Methods of the Domain Class
    */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${specialityName}";
    }
}
