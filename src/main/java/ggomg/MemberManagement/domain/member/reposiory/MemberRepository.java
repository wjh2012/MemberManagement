package ggomg.MemberManagement.domain.member.reposiory;

import ggomg.MemberManagement.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long>, MemberCustomRepository {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByOauthId(String username);

    boolean existsByUsername(String username);

    boolean existsByOauthId(String oauthId);

    @Query("select m from Member m join fetch m.memberRoles m_r join fetch m_r.role")
    List<Member> findAll();

}
