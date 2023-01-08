package pgdp.teams;

import java.util.*;

public class Lineup {
	private final int numberAttackers;
	private final int numberDefenders;
	private final int numberSupporters;
	private Set<Penguin> attackers;
	private Set<Penguin> defenders;
	private Set<Penguin> supporters;

	private int teamScore;
	private int teamSkill;
	private int teamSynergy;

	public Lineup(Set<Penguin> attackers, Set<Penguin> defenders, Set<Penguin> supporters) {
		this.attackers = attackers;
		numberAttackers = attackers.size();
		this.defenders = defenders;
		numberDefenders = defenders.size();
		this.supporters = supporters;
		numberSupporters = supporters.size();
		computeScores();
	}

	/**
	 * Computes the {@code teamSkill}, {@code teamSynergy} and {@code teamScore} for {@code this} {@code LineUp}
	 */
	private void computeScores() {
		// TODO
		//teamSkill is the sum of all "relevant" skill values
		int att_relevant = attackers.stream()
				.mapToInt(att -> att.attack)
				.sum();
		int def_relevant = defenders.stream()
				.mapToInt(def -> def.defence)
				.sum();
		int sup_relevant = supporters.stream()
				.mapToInt(sup -> sup.support)
				.sum();
		teamSkill = att_relevant + def_relevant + sup_relevant;

		//teamSynergy is the sum of all synergies, for every pair that exists
		Set<Penguin> allPenguins = new HashSet<>();
		allPenguins.addAll(attackers);
		allPenguins.addAll(defenders);
		allPenguins.addAll(supporters);
		Set<Penguin> checkedPenguins = new HashSet<>();
		List<Integer> allSynergies = new ArrayList<>();

		allPenguins.stream()
				.forEach(penguin -> {
					checkedPenguins.add(penguin);
					allPenguins.stream()
							.forEach(otherPengu -> allSynergies.add(
									checkedPenguins.contains(otherPengu) ? 0 : penguin.getSynergy(otherPengu)));
				});

		teamSynergy = allSynergies.stream().mapToInt(n -> n).sum();

		//teamScore is sum of teamSkill and teamSynergy
		teamScore = teamSkill + teamSynergy;
	}

	public int getTeamScore() {
		return teamScore;
	}

	public int getTeamSkill() {
		return teamSkill;
	}

	public int getTeamSynergy() {
		return teamSynergy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Line-up: ").append(numberAttackers).append(" - ").append(numberDefenders).append(" - ")
				.append(numberSupporters).append("\n");
		sb.append("Team Score: ").append(teamScore).append("\n");
		sb.append("Team Skill: ").append(teamSkill).append("\n");
		sb.append("Team Synergy: ").append(teamSynergy).append("\n\n");
		sb.append("Attackers: \n");
		for (Penguin p : attackers) {
			sb.append("\t").append(p.toString()).append("\n");
		}
		sb.append("\nDefenders: \n");
		for (Penguin p : defenders) {
			sb.append("\t").append(p.toString()).append("\n");
		}
		sb.append("\nSupporters: \n");
		for (Penguin p : supporters) {
			sb.append("\t").append(p.toString()).append("\n");
		}
		return sb.toString();
	}

	/**
	 * Computes the optimal {@code LineUp} for the given parameters
	 * @param players a set of all available {@code Penguin} players
	 * @param numberAttackers the number of attackers in the {@code LineUp}
	 * @param numberDefenders the number of defenders in the {@code LineUp}
	 * @param numberSupporters the number of defender in the {@code LineUp}
	 * @return a {@code LineUp} with optimal configuration
	 */
	public static Lineup computeOptimalLineup(Set<Penguin> players, int numberAttackers, int numberDefenders,
			int numberSupporters) {
		// TODO
		return null;
	}

	public static void main(String[] args) {
		final boolean testComputeScores = true;
		final boolean testComputeOptimalLineup = false;
		final boolean testSmallExample = true;
		final boolean testLargeExample = false;

		if (testComputeScores) {
			// example: computeScores small
			if (testSmallExample) {
				Penguin jonas = new Penguin("Jonas", 10, 0, 0);
				Penguin anatoly = new Penguin("Anatoly", 10, 10, 0);
				Penguin julian = new Penguin("Juilan", 10, 10, 0);
				Penguin simon = new Penguin("Simon", 0, 0, 10);
				Penguin.setSynergy(jonas, anatoly, 10);
				Penguin.setSynergy(jonas, julian, 5);

				Lineup l0 = new Lineup(Set.of(jonas, anatoly), Set.of(julian), Set.of(simon));
				System.out.println(l0);
			}

			// example: computeScores large
			if (testLargeExample) {
				Penguin eve = new Penguin("Eve", 9151, 5, 11);
				Penguin enrico = new Penguin("Enrico", 97, 103, 3499);
				Penguin hanna = new Penguin("Hanna", 6367, 331, 337);
				Penguin sachmi = new Penguin("Sachmi", 103, 5701, 109);
				Penguin jasmine = new Penguin("Jasmine", 233, 5737, 239);
				Penguin jakob = new Penguin("Jakob", 307, 313, 3559);

				Penguin.setSynergy(eve, hanna, 30);
				Penguin.setSynergy(enrico, jakob, 77);
				Penguin.setSynergy(sachmi, jasmine, 121);
				Penguin.setSynergy(jasmine, jakob, 34);
				Penguin.setSynergy(eve, sachmi, 1);

				Lineup l1 = new Lineup(Set.of(eve, hanna), Set.of(sachmi, jasmine), Set.of(enrico, jakob));
				System.out.println(l1);
			}
		}

		if (testComputeOptimalLineup) {
			// example: computeOptimalLineup small
			if (testSmallExample) {
				Penguin eric = new Penguin("Eric", 10, 0, 0);
				Penguin nils = new Penguin("Nils", 10, 10, 0);
				Penguin felix = new Penguin("Felix", 10, 10, 0);
				Penguin thomas = new Penguin("Thomas", 0, 0, 10);

				Penguin.setSynergy(eric, nils, 20);
				Penguin.setSynergy(eric, felix, 5);

				Lineup l2 = Lineup.computeOptimalLineup(Set.of(eric, nils, felix, thomas), 2, 1, 1);
				System.out.println(l2 + "\n");
			}

			// example: computeOptimalLineup large
			if (testLargeExample) {
				Penguin jan = new Penguin("Jan", -101, 177013, 777);
				Penguin georg = new Penguin("Georg", 9001, -25984, 66);
				Penguin anton = new Penguin("Anton", 300, 5180, -20000);
				Penguin johannes = new Penguin("Johannes", 0, 314, 2792);
				Penguin konrad = new Penguin("Konrad", 420, 8008, 911);
				Penguin max = new Penguin("Max", 1337, -161, 69);
				Penguin oliver = new Penguin("Oliver", 1, 271, 2319);
				Penguin robin = new Penguin("Robin", 13, 34, 666);
				Penguin laura = new Penguin("Laura", -37, 577, 1459);
				Penguin lukas = new Penguin("Lukas", -79, 549, 1123);

				Penguin.setSynergy(georg, max, 1137);
				Penguin.setSynergy(max, oliver, 33);
				Penguin.setSynergy(max, konrad, 9);
				Penguin.setSynergy(georg, anton, 2187);
				Penguin.setSynergy(oliver, anton, 1138);
				Penguin.setSynergy(jan, lukas, 883);
				Penguin.setSynergy(jan, laura, 787);
				Penguin.setSynergy(johannes, oliver, 420);
				Penguin.setSynergy(johannes, jan, 69);

				Lineup l3 = Lineup.computeOptimalLineup(
						Set.of(jan, georg, anton, johannes, konrad, max, oliver, robin, laura, lukas), 2, 3, 5);

				System.out.println(l3);
			}
		}
	}
}
