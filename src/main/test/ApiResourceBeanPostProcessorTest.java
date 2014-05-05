import com.github.lemniscate.lib.sra.annotation.ApiResourceBeanPostProcessor;
import com.github.lemniscate.lib.sra.repo.ResourceRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @Author dave 4/30/14 9:27 PM
 */
public class ApiResourceBeanPostProcessorTest {


    private DefaultListableBeanFactory factory;

    @Before
    public void setUp() {

        factory = new DefaultListableBeanFactory();

        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(factory);
        factory.addBeanPostProcessor(processor);
    }

    @Test
    public void foo(){
        ApiResourceBeanPostProcessor instance = new ApiResourceBeanPostProcessor("com.github.lemniscate.stack.boot.model");
        factory.addBeanPostProcessor(instance);
        instance.postProcessBeanDefinitionRegistry(factory);

        Map<String, ResourceRepository> repos = factory.getBeansOfType(ResourceRepository.class);
        Assert.notEmpty(repos);
    }

}
