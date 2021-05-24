package it.objectmethod.ecommerce.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import it.objectmethod.ecommerce.repository.CartArticleRepository;

@Component
public class ScheduledTasks {

	@Autowired
	CartArticleRepository cartArticleRepo;

	@Scheduled(cron = "${scheduler.cron.expression.lastOrder}")
	private void showLastCartArticle() {
//		cartArticleRepo.
	}
}
