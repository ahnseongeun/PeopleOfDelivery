package SoftSquard.PeopleOfDelivery.domain.catrgory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void cleanUp(){
        categoryRepository.deleteAll();
        categoryRepository.save(new Category("1","/test"));
    }

    @Test
    public void 카테고리가_잘들어가는지_확인() throws Exception {
        // when
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        // then

        assertEquals(categories.get(0).getId(),1);
        assertThat(categories).hasSizeGreaterThan(0);
        System.out.println("카테고리 데이터 들어가는 것 확인");
    }

}



//package SoftSquard.PeopleOfDelivery.domain.category;
//
//        import SoftSquard.PeopleOfDelivery.domain.catrgory.Category;
//        import SoftSquard.PeopleOfDelivery.domain.catrgory.CategoryRepository;
//        import SoftSquard.PeopleOfDelivery.domain.user.User;
//        import org.junit.Before;
//        import org.junit.jupiter.api.Test;
//        import org.junit.runner.RunWith;
//        import org.springframework.beans.factory.annotation.Autowired;
//        import org.springframework.boot.test.context.SpringBootTest;
//        import org.springframework.test.context.junit4.SpringRunner;
//        import java.util.List;
//
//        import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CategoryRepositoryTest {
//
//    @Autowired
//    private CategoryRepository categoryRepository;
//
//    @Before
//    public void cleanUp() throws Exception {
//        categoryRepository.deleteAll();
//        categoryRepository.save(new Category("한식","/test"));
//    }

//    @Test
//    public void 모든_리스트_가져오기() throws Exception {
//
//        // when
//        final List<Category> categories = (List<Category>) categoryRepository.findAll();
//        // then
//        assertThat(categories).hasSizeGreaterThan(0);
//    }

//    /* 추가 */
//    @Test
//    public void 김씨_성_찾기() throws Exception {
//
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//
//        QPerson person = QPerson.person;
//
//        long result = queryFactory.selectFrom(person).where(person.lastName.eq("김")).fetchCount(); // 성이 김씨인 사람 찾기
//        List<Person> result2 = queryFactory.selectFrom(person).where(person.lastName.eq("김"), person.email.like("%.com")).fetch(); // 성이 김씨이고 메일 뒤가 com으로 끝나는 사람 찾기
//        QueryResults<Person> result3 = queryFactory.selectFrom(person).where(person.lastName.eq("김"), person.age.between(20,40)).fetchResults(); // 성이 김씨이고 나이가 20~40세 사이인 사람 찾기
//
//        assertThat(result).isEqualTo(3);
//        assertThat(result2.get(0).getFirstName()).isEqualTo("철수");
//        assertThat(result3.getTotal()).isEqualTo(2);
//
//    }
