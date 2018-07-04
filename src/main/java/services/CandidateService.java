package services;

import entities.Candidate;
import entities.Vote;
import entities.Voter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Repository
public class CandidateService {

    @PersistenceContext
    private EntityManager em;

    public List<Candidate> getAvailableCandidatesForVoter(Voter v) {
        return em
                .createNamedQuery("Candidate.getAvailableForVoter", Candidate.class)
                .setParameter("voter", v)
                .getResultList();
    }

    public List<Candidate> getRankings() {
        List<Candidate> candidates = em
                .createNamedQuery("Candidate.getAllVoted", Candidate.class)
                .getResultList();

        candidates.forEach(x -> x.setTotalVotes(
                x.getVotesByCandidateId()
                        .stream()
                        .mapToInt(Vote::getVote)
                        .sum()
        ));

        candidates.sort(Comparator.comparing(Candidate::getTotalVotes).reversed());

        return candidates;
    }
}
