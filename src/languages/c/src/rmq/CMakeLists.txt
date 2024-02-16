
set(TARGET_NAME rmq)

add_library(${TARGET_NAME} OBJECT
    rmq.c
)

target_include_directories(${TARGET_NAME}
    PUBLIC include
)

add_subdirectory(cli_options_parser)
target_link_libraries(${TARGET_NAME}
    cli_options_parser
)

