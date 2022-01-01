import com.zhangzhuorui.framework.rbacsystem.RbacSystemApplication;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2022/1/1 上午10:10
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RbacSystemApplication.class)
@Transactional
@Rollback
public class ZtTest {

    @Test
    public void testGroovyShell() {
        Binding binding = new Binding();
        binding.setVariable("A", 2);
        binding.setVariable("B", 3);
        // binding.setVariable("language", "Groovy");
        GroovyShell shell = new GroovyShell(binding);
        shell.setVariable("C", 4);
        Object F1 = shell.evaluate("P1=A*B+C; return P1 ");
        Object F2 = shell.evaluate("return A*B+C");
        Object F3 = shell.evaluate("A*B+C");
        System.out.println(F1);
        System.out.println(F2);
        System.out.println(F3);
    }

}
