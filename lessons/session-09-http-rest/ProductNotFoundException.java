// A CUSTOM exception. Extending RuntimeException makes this an
// UNCHECKED exception — meaning callers are not FORCED by the compiler
// to catch it or declare it. Java also has CHECKED exceptions (extend
// "Exception" instead), where the compiler forces every caller to either
// catch them or declare "throws SomeException" on their own method.
//
// Classic checked examples from the standard library: IOException,
// SQLException — things caused by the outside world (a missing file, a
// dead database connection) that calling code genuinely can't ignore.
//
// We're choosing UNCHECKED here on purpose, because "product not found"
// is a business rule violation, not an environmental failure, and this
// mirrors real practice: Spring Boot REST APIs (which we'll build soon)
// almost always use unchecked exceptions for business errors, then catch
// them in ONE central place to turn them into proper HTTP error
// responses, rather than forcing every single method up the call chain
// to mention "throws ProductNotFoundException".
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productName) {
        // "super(...)" here calls RuntimeException's constructor, which
        // stores this message so it shows up when the exception prints.
        super("Product not found: " + productName);
    }
}
