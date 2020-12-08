package br.com.jofofe.campanha.loader;

import br.com.jofofe.campanha.entidades.Time;
import br.com.jofofe.campanha.repository.TimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseLoader implements CommandLineRunner {

	private final TimeRepository timeRepository;

	public DataBaseLoader(TimeRepository timeRepository) {
		this.timeRepository = timeRepository;
	}

	@Override
	public void run(String... strings) {
		this.timeRepository.save(Time.builder().id(1).nomeTime("GALO").build());
		this.timeRepository.save(Time.builder().id(2).nomeTime("PALMEIRAS").build());
		this.timeRepository.save(Time.builder().id(3).nomeTime("GRAMIO").build());
		this.timeRepository.save(Time.builder().id(4).nomeTime("VASCO").build());
	}
}