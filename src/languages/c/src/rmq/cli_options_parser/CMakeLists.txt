
set(TARGET_NAME cli_options_parser)

add_library(${TARGET_NAME} STATIC
    cli_options_parser.c
)

target_include_directories(${TARGET_NAME}
    PUBLIC include
)
