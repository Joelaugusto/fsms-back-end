package joel.fsms.config.notification.EMAIL;

import joel.fsms.config.notification.EMAIL.channel.MailChannel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@Component
public class NotificationsContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        NotificationsContextHolder.applicationContext = applicationContext;
    }

    public static TemplateEngine getTemplateEngine() {
        return applicationContext.getBean(TemplateEngine.class);
    }

    public static MailChannel getChannel() {
        NamedBeanHolder<MailChannel> mailChannelNamedBeanHolder = applicationContext.getAutowireCapableBeanFactory().resolveNamedBean(MailChannel.class);
        return mailChannelNamedBeanHolder.getBeanInstance();
    }
}
