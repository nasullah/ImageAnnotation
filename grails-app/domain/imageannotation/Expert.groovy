package imageannotation

class Expert {

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
}
