package com.alexrdclement.uiplayground.app.navigation.deeplink

import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsDeeplinkTree
import com.alexrdclement.uiplayground.app.navigation.PathSegment
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey

private val deeplinkTree = deeplinkTree {
    route(MainCatalogRoute)
    route(ConfigurationRoute)
    experimentsDeeplinkTree()
}

private val flattenedNodes: List<DeeplinkNode> = buildList {
    fun addNodes(nodes: List<DeeplinkNode>) {
        nodes.forEach { node ->
            add(node)
            addNodes(node.children)
        }
    }
    addNodes(deeplinkTree)
}

fun UiPlaygroundNavKey.toDeeplinkPath(): String {
    val node = flattenedNodes.firstOrNull { it.navKeyClass == this::class }
        ?: return pathSegment.value

    val parentPath = node.parent?.toDeeplinkPath()

    return if (parentPath != null) {
        "$parentPath/${pathSegment.value}"
    } else {
        pathSegment.value
    }
}

fun UiPlaygroundNavKey.Companion.fromDeeplinkPath(deeplink: String): UiPlaygroundNavKey? {
    val segments = deeplink.split("/").filter { it.isNotEmpty() }.map(::PathSegment)
    return segments.toBackStack().lastOrNull()
}

fun List<PathSegment>.toBackStack(): List<UiPlaygroundNavKey> {
    val segments = this

    fun matchRoute(
        nodes: List<DeeplinkNode>,
        segmentIndex: Int,
        accumulated: List<UiPlaygroundNavKey>,
    ): List<UiPlaygroundNavKey>? {
        if (segmentIndex >= segments.size) return null

        val segment = segments[segmentIndex]

        for (node in nodes) {
            val isMatch = node.pathSegment == PathSegment.Wildcard || node.pathSegment == segment
            if (!isMatch) continue

            val parsed = node.parser(segment) ?: continue
            val newAccumulated = accumulated + parsed

            // Try to match children first
            if (node.children.isNotEmpty() && segmentIndex + 1 < segments.size) {
                val childMatch = matchRoute(node.children, segmentIndex + 1, newAccumulated)
                if (childMatch != null) return childMatch
            }

            // If we're at the last segment or no children matched, return accumulated list
            if (segmentIndex == segments.size - 1) {
                return newAccumulated
            }
        }

        return null
    }

    return matchRoute(
        nodes = deeplinkTree,
        segmentIndex = 0,
        accumulated = emptyList(),
    ) ?: emptyList()
}
