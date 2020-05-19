package test;

import javax.print.attribute.standard.Media;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jobs")
public class JobResource {

    private JobRepository repository;
    private JobReactiveRepository reactiveRepository;

    public JobResource(JobRepository repository, JobReactiveRepository reactiveRepository) {
        this.repository = repository;
        this.reactiveRepository = reactiveRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Job getJob(Long id) {
        return repository.findById(id);
    }

    @POST
    @Transactional
    public void createJob(String name) {
        Job job = new Job(name);

        // Blocking persistence
        repository.persist(job);

        // Reactive persistence
        reactiveRepository.persist(job).subscribe().with(
            result -> System.err.println(result),
            exception -> System.err.println(exception));
    }
}