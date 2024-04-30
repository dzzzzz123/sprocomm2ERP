package ext.kingdeeERP.supply;

import ext.kingdeeERP.util.CommonUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

public class Servlet implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Entity> list = CommonUtil.getEntitiesFromRequest(request, Entity.class, "");
        ResultEntity result = Service.process(list);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(CommonUtil.parseJson(result));
        out.close();
        return null;
    }
}
