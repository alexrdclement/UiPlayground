package com.alexrdclement.uiplayground.app.navigation.deeplink

import com.alexrdclement.uiplayground.app.navigation.PathSegment
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import kotlin.reflect.KClass

data class DeeplinkNode(
    val pathSegment: PathSegment,
    val navKeyClass: KClass<out UiPlaygroundNavKey>,
    val parser: (PathSegment) -> UiPlaygroundNavKey?,
    val parent: UiPlaygroundNavKey?,
    val children: List<DeeplinkNode>,
)

class DeeplinkTreeBuilder(
    val parent: UiPlaygroundNavKey? = null,
) {
    val nodes = mutableListOf<DeeplinkNode>()

    inline fun <reified T : UiPlaygroundNavKey> route(
        pathSegment: PathSegment,
        noinline parser: (PathSegment) -> UiPlaygroundNavKey?,
        children: DeeplinkTreeBuilder.() -> Unit = {}
    ) {
        val childrenBuilder = DeeplinkTreeBuilder(parent = parser(pathSegment))
        childrenBuilder.children()

        nodes.add(
            DeeplinkNode(
                pathSegment = pathSegment,
                navKeyClass = T::class,
                parser = parser,
                parent = parent,
                children = childrenBuilder.build()
            )
        )
    }

    inline fun <reified T : UiPlaygroundNavKey> wildcardRoute(
        children: DeeplinkTreeBuilder.() -> Unit = {},
        noinline parser: (PathSegment) -> UiPlaygroundNavKey?
    ) {
        val pathSegment = PathSegment("*")
        val childrenBuilder = DeeplinkTreeBuilder(parent = parser(pathSegment))
        childrenBuilder.children()

        nodes.add(
            DeeplinkNode(
                pathSegment = pathSegment,
                navKeyClass = T::class,
                parser = parser,
                parent = parent,
                children = childrenBuilder.build()
            )
        )
    }

    inline fun <reified T : UiPlaygroundNavKey> route(
        navKey: T,
        children: DeeplinkTreeBuilder.() -> Unit = {}
    ) {
        route<T>(
            pathSegment = navKey.pathSegment,
            parser = { navKey },
            children = children,
        )
    }

    fun build(): List<DeeplinkNode> = nodes
}

fun deeplinkTree(block: DeeplinkTreeBuilder.() -> Unit): List<DeeplinkNode> {
    return DeeplinkTreeBuilder().apply(block).build()
}
