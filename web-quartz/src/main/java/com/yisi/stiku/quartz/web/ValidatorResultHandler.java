/*
 * import java.util.List;
 * 
 * import javax.xml.transform.Result;
 * 
 * import org.springframework.validation.BindingResult; import
 * org.springframework.validation.ObjectError;
 * 
 * public class ValidatorResultHandler {
 * 
 * public static Result handle(BindingResult result) {
 * 
 * 
 * if (result.hasErrors()) { List<ObjectError> list = result.getAllErrors();
 * ObjectError oe = list.get(0); retVal.setMessage(oe.getDefaultMessage()); }
 * else { retVal.setStatus(Const.SUCCESS); } return retVal; } }
 */