package unit03;

import java.time.LocalDateTime;
import java.util.*;

interface SessionManager {
    void createSession(LocalDateTime date, String place, Club club);
    void cancelSession(LocalDateTime date, String place, Club club);
    void postponeSession(LocalDateTime date, String place, LocalDateTime newDate, Club club);
}

abstract class Member {
    private String name;
    private LocalDateTime joinDate;
    private String skillLevel;

    public Member(String name, LocalDateTime joinDate) {
        this.name = name;
        this.joinDate = joinDate;
        this.skillLevel = "미정";
    }

    public Member(String name, LocalDateTime joinDate, String skillLevel) {
        this.name = name;
        this.joinDate = joinDate;
        this.skillLevel = skillLevel;
    }

    public String getName() { return name; }
    public LocalDateTime getJoinDate() { return joinDate; }
    public String getSkillLevel() { return skillLevel; }

    public void participateSession(Session session) {
        session.addMember(this);
        System.out.println(name + "가 연습 세션에 참가합니다.");
    }
}

class Admin extends Member implements SessionManager {
    public Admin(String name, LocalDateTime joinDate) {
        super(name, joinDate);
        System.out.println(name + "이 운영진으로 가입되었습니다.");
    }

    @Override
    public void createSession(LocalDateTime date, String place, Club club) {
        Session session = new Session(date, place, this);
        club.addSession(session);
        System.out.println(getName() + "이 " + date.toLocalDate() + ", " + place + "에 연습 세션을 오픈했습니다.");
    }

    @Override
    public void cancelSession(LocalDateTime date, String place, Club club) {
        Session session = club.findSession(date, place);
        if (session != null) {
            session.cancel();
            System.out.println(date.toLocalDate() + ", " + place + " 연습 세션이 취소되었습니다.");
        }
    }

    @Override
    public void postponeSession(LocalDateTime date, String place, LocalDateTime newDate, Club club) {
        Session session = club.findSession(date, place);
        if (session != null) {
            session.reschedule(newDate);
            System.out.println(getName() + "이 " + newDate.toLocalDate() + ", " + place + "에 연습 세션을 연기했습니다.");
        }
    }
}

class RegularMember extends Member implements SessionManager {
    public RegularMember(String name, LocalDateTime joinDate) {
        super(name, joinDate);
        System.out.println(name + "가 일반 멤버로 가입되었습니다.");
    }

    @Override
    public void createSession(LocalDateTime date, String place, Club club) {
        Session session = new Session(date, place, this);
        club.addSession(session);
        System.out.println(getName() + "이 " + date.toLocalDate() + ", " + place + "에 연습 세션을 오픈했습니다.");
    }

    @Override
    public void cancelSession(LocalDateTime date, String place, Club club) {
        System.out.println("일반 멤버는 연습을 취소할 수 없습니다.");
    }

    @Override
    public void postponeSession(LocalDateTime date, String place, LocalDateTime newDate, Club club) {
        System.out.println("일반 멤버는 연습을 연기할 수 없습니다.");
    }
}

class NewMember extends Member {
    public NewMember(String name, LocalDateTime joinDate) {
        super(name, joinDate);
        System.out.println(name + "가 신규 멤버로 가입되었습니다.");
    }
}

class Session {
    private LocalDateTime date;
    private String place;
    private List<Member> participants;
    private Member host;
    private String status;

    public Session(LocalDateTime date, String place, Member host) {
        this.date = date;
        this.place = place;
        this.host = host;
        this.status = "개설";
        this.participants = new ArrayList<>();
    }

    public LocalDateTime getDate() { return date; }
    public String getPlace() { return place; }
    public String getStatus() { return status; }
    public Member getHost() { return host; }

    public void addMember(Member member) {
        participants.add(member);
    }

    public void cancel() {
        status = "취소";
    }

    public void reschedule(LocalDateTime newDate) {
        date = newDate;
    }

    public void printInfo() {
        System.out.print(date.toLocalDate() + ", " + place + ", [");
        for (int i = 0; i < participants.size(); i++) {
            System.out.print(participants.get(i).getName());
            if (i < participants.size() - 1) System.out.print(", ");
        }
        System.out.println("], " + host.getName() + ", " + status);
    }
}

class Club {
    private List<Member> members = new ArrayList<>();
    private List<Session> sessions = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
    }

    public void removeMember(Member member) {
        members.remove(member);
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public Session findSession(LocalDateTime date, String place) {
        for (Session s : sessions) {
            if (s.getDate().equals(date) && s.getPlace().equals(place)) return s;
        }
        return null;
    }

    public List<Session> findSessionsByStatus(String status) {
        List<Session> result = new ArrayList<>();
        for (Session s : sessions) {
            if (s.getStatus().equals(status)) result.add(s);
        }
        return result;
    }
}

public class BiodomeFamily08 {
    public static void main(String[] args) {
        Club club = new Club();

        Admin john = new Admin("John", LocalDateTime.of(2130, 9, 1, 0, 0));
        RegularMember jane = new RegularMember("Jane", LocalDateTime.of(2130, 9, 2, 0, 0));
        RegularMember doe = new RegularMember("Doe", LocalDateTime.of(2130, 9, 2, 0, 0));
        NewMember amy = new NewMember("Amy", LocalDateTime.of(2130, 9, 3, 0, 0));
        NewMember leo = new NewMember("Leo", LocalDateTime.of(2130, 9, 3, 0, 0));

        club.addMember(john);
        club.addMember(jane);
        club.addMember(doe);
        club.addMember(amy);
        club.addMember(leo);

        LocalDateTime sessionDate = LocalDateTime.of(2130, 9, 12, 18, 0);
        john.createSession(sessionDate, "도메 스타디움", club);

        Session session = club.findSession(sessionDate, "도메 스타디움");
        if (session != null) {
            jane.participateSession(session);
            amy.participateSession(session);
            session.printInfo();
            john.postponeSession(sessionDate, "도메 스타디움", sessionDate.plusDays(7), club);
            john.cancelSession(sessionDate.plusDays(7), "도메 스타디움", club);
        }
    }
}
