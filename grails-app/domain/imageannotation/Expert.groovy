package imageannotation

class Expert {

    static auditable = true
    static constraints = {
        familyName()
        givenName()
        email()
        department(nullable: true)
        affiliatedWith()
        speciality(nullable: true)
    }

    String familyName
    String givenName
    String email
    String department
    Centre affiliatedWith
    Speciality speciality

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${givenName} ${familyName}";
    }
}
