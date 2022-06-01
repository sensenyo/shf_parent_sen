package base;

import org.springframework.ui.Model;

public class BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";

    public String toSuccessPage(Model model, String message)
    {
        model.addAttribute("messagePage",message);
        return PAGE_SUCCESS;
    }
}
