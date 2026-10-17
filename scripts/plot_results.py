import csv
from pathlib import Path

import matplotlib.pyplot as plt


RESULTS = Path("results")
PLOTS = RESULTS / "plots"

PLOTS.mkdir(parents=True, exist_ok=True)


def read_csv(filename):
    path = RESULTS / filename

    with open(path, newline="", encoding="utf-8") as file:
        return list(csv.DictReader(file))


def plot_mst():
    rows = read_csv("mst_benchmark.csv")

    vertices = [int(row["vertices"]) for row in rows]
    kruskal = [float(row["kruskal_avg_ms"]) for row in rows]
    prim = [float(row["prim_avg_ms"]) for row in rows]

    plt.figure(figsize=(8, 5))

    plt.plot(vertices, kruskal, marker="o", label="Kruskal")
    plt.plot(vertices, prim, marker="o", label="Prim")

    plt.xlabel("Number of vertices")
    plt.ylabel("Average execution time (ms)")
    plt.title("MST execution time vs number of vertices")
    plt.legend()
    plt.grid(True)

    plt.tight_layout()
    plt.savefig(PLOTS / "mst_time_vs_vertices.png", dpi=200)
    plt.close()


def plot_mst_edges():
    rows = read_csv("mst_benchmark.csv")

    edges = [int(row["edges"]) for row in rows]
    kruskal = [float(row["kruskal_avg_ms"]) for row in rows]
    prim = [float(row["prim_avg_ms"]) for row in rows]

    plt.figure(figsize=(8, 5))

    plt.plot(edges, kruskal, marker="o", label="Kruskal")
    plt.plot(edges, prim, marker="o", label="Prim")

    plt.xlabel("Number of edges")
    plt.ylabel("Average execution time (ms)")
    plt.title("MST execution time vs number of edges")
    plt.legend()
    plt.grid(True)

    plt.tight_layout()
    plt.savefig(PLOTS / "mst_time_vs_edges.png", dpi=200)
    plt.close()


def plot_connectivity():
    rows = read_csv("connectivity_benchmark.csv")

    vertices = [int(row["vertices"]) for row in rows]
    scc = [float(row["scc_avg_ms"]) for row in rows]
    topo = [float(row["topological_avg_ms"]) for row in rows]

    plt.figure(figsize=(8, 5))

    plt.plot(vertices, scc, marker="o", label="SCC")
    plt.plot(vertices, topo, marker="o", label="Topological Sort")

    plt.xlabel("Number of vertices")
    plt.ylabel("Average execution time (ms)")
    plt.title("Connectivity algorithms execution time")
    plt.legend()
    plt.grid(True)

    plt.tight_layout()
    plt.savefig(PLOTS / "connectivity_time_vs_vertices.png", dpi=200)
    plt.close()


def main():
    plot_mst()
    plot_mst_edges()
    plot_connectivity()

    print("Plots created successfully.")
    print("Location:", PLOTS.resolve())


if __name__ == "__main__":
    main()